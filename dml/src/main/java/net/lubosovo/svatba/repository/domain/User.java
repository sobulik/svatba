package net.lubosovo.svatba.repository.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Email;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Basic;

import java.io.Serializable;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "users")
public class User implements DomainObject, Serializable {

    private static final long serialVersionUID = -6268457245178589231L;
    private Long id;
    private Set<Gift> gifts;
    private String name;
    private String phone;
    private String email;
    private String login;
    private String password;

    protected User() {
    }

    public User(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "user")
    public Set<Gift> getGifts() {
        if (this.gifts == null) {
            this.gifts = new HashSet<Gift>();
        }
        return this.gifts;
    }

    protected void setGifts(final Set<Gift> gifts) {
        this.gifts = gifts;
    }

    @Basic(optional = false)
    public String getName() {
        return this.name;
    }

    @Length(max = 64) //fiks
    @NotEmpty
    public void setName(final String name) {
        this.name = name;
    }

    @Basic
    public String getPhone() {
        return this.phone;
    }

    @Length(max = 16)
    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Basic
    public String getEmail() {
        return this.email;
    }

    @Email
    @Length(max = 64)
    public void setEmail(final String email) {
        this.email = email;
    }

    @Basic
    public String getLogin() {
        return this.login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    @Basic
    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public final boolean equals(final Object object) {
        if (object instanceof User) {
            User user = (User) object;
            boolean equals = true;
            if (equals) {
                if (this.name != null) {
                    equals = this.name.equals(user.name);
                } else if (user.name != null) {
                    equals = false;
                }
            }
            return equals;
        }
        return false;
    }

    @Override
    public final int hashCode() {
        int hashCode = 0;
        if (this.name != null) {
            hashCode += this.name.hashCode();
        }
        return hashCode;
    }
}
