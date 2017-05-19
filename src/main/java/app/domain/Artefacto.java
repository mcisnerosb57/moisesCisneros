package app.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Artefacto.
 */
@Entity
@Table(name = "artefacto")
public class Artefacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "versiona", nullable = false)
    private String versiona;

    @NotNull
    @Column(name = "repositorio", nullable = false)
    private String repositorio;

    @Column(name = "comprobado")
    private Boolean comprobado;

    @Column(name = "existe")
    private Boolean existe;

    @Column(name = "grupo")
    private String grupo;

    @ManyToOne
    @NotNull
    private Version version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Artefacto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVersiona() {
        return versiona;
    }

    public Artefacto versiona(String versiona) {
        this.versiona = versiona;
        return this;
    }

    public void setVersiona(String versiona) {
        this.versiona = versiona;
    }

    public String getRepositorio() {
        return repositorio;
    }

    public Artefacto repositorio(String repositorio) {
        this.repositorio = repositorio;
        return this;
    }

    public void setRepositorio(String repositorio) {
        this.repositorio = repositorio;
    }

    public Boolean isComprobado() {
        return comprobado;
    }

    public Artefacto comprobado(Boolean comprobado) {
        this.comprobado = comprobado;
        return this;
    }

    public void setComprobado(Boolean comprobado) {
        this.comprobado = comprobado;
    }

    public Boolean isExiste() {
        return existe;
    }

    public Artefacto existe(Boolean existe) {
        this.existe = existe;
        return this;
    }

    public void setExiste(Boolean existe) {
        this.existe = existe;
    }

    public String getGrupo() {
        return grupo;
    }

    public Artefacto grupo(String grupo) {
        this.grupo = grupo;
        return this;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Version getVersion() {
        return version;
    }

    public Artefacto version(Version version) {
        this.version = version;
        return this;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Artefacto artefacto = (Artefacto) o;
        if(artefacto.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, artefacto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Artefacto{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            ", versiona='" + versiona + "'" +
            ", repositorio='" + repositorio + "'" +
            ", comprobado='" + comprobado + "'" +
            ", existe='" + existe + "'" +
            ", grupo='" + grupo + "'" +
            '}';
    }
}
