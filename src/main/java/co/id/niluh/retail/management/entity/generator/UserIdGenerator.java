package co.id.niluh.retail.management.entity.generator;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
		Connection connection = sharedSessionContractImplementor.connection();
		try {
			Statement statement = connection.createStatement();
			String prefix = new SimpleDateFormat("yyyyMMdd").format(new Date());
			ResultSet rs = statement.executeQuery("select cast(max(right(user_id,6)) as integer) as user_id from mst_user where left(user_id,8) = '" + prefix + "'");
			if (rs.next()) {
				int id = rs.getInt(1) + 1;
				String generatedId = prefix + StringUtils.leftPad(Integer.valueOf(id).toString(), 6, "0");
				return generatedId;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
