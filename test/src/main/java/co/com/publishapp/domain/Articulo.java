package co.com.publishapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Articulo.
 */
@Entity
@Table(name = "articulo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Articulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "titulo")
    private String titulo;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "articulo_publicacion",
               joinColumns = @JoinColumn(name = "articulo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "publicacion_id", referencedColumnName = "id"))
    private Set<Publicacion> publicacions = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "articulo_autor",
               joinColumns = @JoinColumn(name = "articulo_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "autor_id", referencedColumnName = "id"))
    private Set<Autor> autors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("articulos")
    private Categoria categoria;

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

    public Articulo fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public Articulo titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Publicacion> getPublicacions() {
        return publicacions;
    }

    public Articulo publicacions(Set<Publicacion> publicacions) {
        this.publicacions = publicacions;
        return this;
    }

    public Articulo addPublicacion(Publicacion publicacion) {
        this.publicacions.add(publicacion);
        publicacion.getArticulos().add(this);
        return this;
    }

    public Articulo removePublicacion(Publicacion publicacion) {
        this.publicacions.remove(publicacion);
        publicacion.getArticulos().remove(this);
        return this;
    }

    public void setPublicacions(Set<Publicacion> publicacions) {
        this.publicacions = publicacions;
    }

    public Set<Autor> getAutors() {
        return autors;
    }

    public Articulo autors(Set<Autor> autors) {
        this.autors = autors;
        return this;
    }

    public Articulo addAutor(Autor autor) {
        this.autors.add(autor);
        autor.getArticulos().add(this);
        return this;
    }

    public Articulo removeAutor(Autor autor) {
        this.autors.remove(autor);
        autor.getArticulos().remove(this);
        return this;
    }

    public void setAutors(Set<Autor> autors) {
        this.autors = autors;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Articulo categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Articulo)) {
            return false;
        }
        return id != null && id.equals(((Articulo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Articulo{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
