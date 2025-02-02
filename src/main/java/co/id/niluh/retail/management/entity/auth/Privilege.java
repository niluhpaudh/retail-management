package co.id.niluh.retail.management.entity.auth;

import co.id.niluh.retail.management.entity.auth.Role;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@Entity
@Table(name = "mst_privilege")
public class Privilege implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7688010122482005246L;

    @Id
    @Length(max = 50)
    @Column(name = "privilege_id")
    private String privilegeId;

    @Length(max = 255)
    @Column(name = "description")
    private String description;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mst_role_privilege", joinColumns = @JoinColumn(name = "privilege_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Privilege() {
        super();
    }
}