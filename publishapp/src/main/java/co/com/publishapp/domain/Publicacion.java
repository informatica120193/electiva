package co.com.publishapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Publicacion.
 */
@Entity
@Table(name = "publicacion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Publicacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "numero")
    private String numero;

    @ManyToMany(mappedBy = "publicacions")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Articulo> articulos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Publicacion fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public Publicacion numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Set<Articulo> getArticulos() {
        return articulos;
    }

    public Publicacion articulos(Set<Articulo> articulos) {
        this.articulos = articulos;
        return this;
    }

    public Publicacion addArticulo(Articulo articulo) {
        this.articulos.add(articulo);
        articulo.getPublicacions().add(this);
        return this;
    }

    public Publicacion removeArticulo(Articulo articulo) {
        this.articulos.remove(articulo);
        articulo.getPublicacions().remove(this);
        return this;
    }

    public void setArticulos(Set<Articulo> articulos) {
        this.articulos = articulos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Publicacion)) {
            return false;
        }
        return id != null && id.equals(((Publicacion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Publicacion{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}
