package net.lubosovo.svatba.repository.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Basic;

@Entity
@Table(name = "gifts")
public class Gift implements DomainObject, Serializable {

    private static final long serialVersionUID = 7396461399823389395L;
    private Long id;
    private User user;
    private String title;
    private String descrip;
    private String link;
    private Long price;
    private String thumb; //fiks make it Lob

    protected Gift() {
    }

    public Gift(String title) {
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return this.id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "id_user")
    public User getUser() {
        return this.user;
    }

    protected void setUser(final User user) {
        this.user = user;
    }

    public void assocUser(final User user) {
        if (user != null) {
            user.getGifts().add(this);
        } else {
            if (this.user != null) {
                this.user.getGifts().remove(this);
            }
        }
        this.user = user;
    }

    @Basic(optional = false)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Basic
    public String getDescrip() {
        return this.descrip;
    }

    public void setDescrip(final String descrip) {
        this.descrip = descrip;
    }

    @Basic
    public String getLink() {
        return this.link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    @Basic
    public Long getPrice() {
        return this.price;
    }

    public void setPrice(final Long price) {
        this.price = price;
    }

    @Basic
    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(final String thumb) {
        this.thumb = thumb;
    }

    @Override
    public final boolean equals(final Object object) {
        if (object instanceof Gift) {
            Gift gift = (Gift) object;
            boolean equals = true;
            if (equals) {
                if (this.title != null) {
                    equals = this.title.equals(gift.title);
                } else if (gift.title != null) {
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
        if (this.title != null) {
            hashCode += this.title.hashCode();
        }
        return hashCode;
    }
}
