package app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Version.
 */
@Entity
@Table(name = "version")
public class Version implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "versionapp", nullable = false)
    private String versionapp;

    @ManyToOne
    @NotNull
    private Aplicacion aplicacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersionapp() {
        return versionapp;
    }

    public Version versionapp(String versionapp) {
        this.versionapp = versionapp;
        return this;
    }

    public void setVersionapp(String versionapp) {
        this.versionapp = versionapp;
    }

    public Aplicacion getAplicacion() {
        return aplicacion;
    }

    public Version aplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
        return this;
    }

    public void setAplicacion(Aplicacion aplicacion) {
        this.aplicacion = aplicacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Version version = (Version) o;
        if(version.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, version.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Version{" +
            "id=" + id +
            ", versionapp='" + versionapp + "'" +
            '}';
    }
}
