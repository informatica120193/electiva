package co.com.publishapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Ciudad.
 */
@Entity
@Table(name = "ciudad")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ciudad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "habitantes")
    private Long habitantes;

    @OneToMany(mappedBy = "ciudad")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Universidad> universidads = new HashSet<>();

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

    public Ciudad nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Ciudad descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getHabitantes() {
        return habitantes;
    }

    public Ciudad habitantes(Long habitantes) {
        this.habitantes = habitantes;
        return this;
    }

    public void setHabitantes(Long habitantes) {
        this.habitantes = habitantes;
    }

    public Set<Universidad> getUniversidads() {
        return universidads;
    }

    public Ciudad universidads(Set<Universidad> universidads) {
        this.universidads = universidads;
        return this;
    }

    public Ciudad addUniversidad(Universidad universidad) {
        this.universidads.add(universidad);
        universidad.setCiudad(this);
        return this;
    }

    public Ciudad removeUniversidad(Universidad universidad) {
        this.universidads.remove(universidad);
        universidad.setCiudad(null);
        return this;
    }

    public void setUniversidads(Set<Universidad> universidads) {
        this.universidads = universidads;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ciudad)) {
            return false;
        }
        return id != null && id.equals(((Ciudad) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ciudad{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", habitantes=" + getHabitantes() +
            "}";
    }
}
