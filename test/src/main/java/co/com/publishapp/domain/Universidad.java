package co.com.publishapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Universidad.
 */
@Entity
@Table(name = "universidad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Universidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "universidad_autor",
               joinColumns = @JoinColumn(name = "universidad_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "autor_id", referencedColumnName = "id"))
    private Set<Autor> autors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("universidads")
    private Ciudad ciudad;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Universidad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Universidad direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<Autor> getAutors() {
        return autors;
    }

    public Universidad autors(Set<Autor> autors) {
        this.autors = autors;
        return this;
    }

    public Universidad addAutor(Autor autor) {
        this.autors.add(autor);
        autor.getUniversidads().add(this);
        return this;
    }

    public Universidad removeAutor(Autor autor) {
        this.autors.remove(autor);
        autor.getUniversidads().remove(this);
        return this;
    }

    public void setAutors(Set<Autor> autors) {
        this.autors = autors;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public Universidad ciudad(Ciudad ciudad) {
        this.ciudad = ciudad;
        return this;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Universidad)) {
            return false;
        }
        return id != null && id.equals(((Universidad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Universidad{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", direccion='" + getDireccion() + "'" +
            "}";
    }
}
