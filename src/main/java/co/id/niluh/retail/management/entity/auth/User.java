package co.id.niluh.retail.management.entity.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import co.id.niluh.retail.management.entity.AuditingEntity;
import co.id.niluh.retail.management.enumz.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Entity
@Table(name = "mst_user")
public class User extends AuditingEntity {
    /**
     *
     */
    private static final long serialVersionUID = -7650255910567270992L;

    private static PasswordEncoder DEFAULT_ENCODER = new BCryptPasswordEncoder();

    @Id
    @Column(name = "user_id")
    @GenericGenerator(name = "user_id", strategy = "co.id.niluh.retail.management.entity.generator.UserIdGenerator")
    @GeneratedValue(generator = "user_id")
    @Length(max = 15)
    private String userId;

    @NotNull
    @Column(name = "user_name")
    @Length(max = 20)
    private String userName;

    @NotNull
    @Column(name = "password")
    @Length(max = 255)
    private String password;

    @NotNull
    @Column(name = "name")
    @Length(max = 150)
    private String name;

    @Column(name = "login_failed")
    private Integer loginFailed;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Column(name = "login_status", columnDefinition="boolean default false")
    private Boolean loginStatus;

    @ManyToMany
    @JoinTable(
            name = "mst_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLoginFailed() {
        return loginFailed;
    }

    public void setLoginFailed(Integer loginFailed) {
        this.loginFailed = loginFailed;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Boolean isLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }



    @Transient
    public void updatePassword(PasswordEncoder encoder, String newPassword){
        if(encoder == null){
            encoder = DEFAULT_ENCODER;
        }
        this.password = encoder.encode(newPassword);
    }

    @Transient
    public Set<String> getRolePrivileges() {
        if(this.roles == null){
            return new HashSet<>();
        }

        return this.roles.stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .distinct()
                .map(Privilege::getPrivilegeId)
                .collect(Collectors.toSet());
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }




}
