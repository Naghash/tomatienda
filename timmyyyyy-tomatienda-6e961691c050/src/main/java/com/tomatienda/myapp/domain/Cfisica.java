package com.tomatienda.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Cfisica.
 */
@Entity
@Table(name = "cfisica")
public class Cfisica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "importe")
    private Double importe;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "codigo")
    private Integer codigo;

    @ManyToOne
    private Empleado empleado;

    @ManyToMany
    @JoinTable(name = "cfisica_producto",
               joinColumns = @JoinColumn(name="cfisicas_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="productos_id", referencedColumnName="id"))
    private Set<Producto> productos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getImporte() {
        return importe;
    }

    public Cfisica importe(Double importe) {
        this.importe = importe;
        return this;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Cfisica fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Cfisica codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Cfisica empleado(Empleado empleado) {
        this.empleado = empleado;
        return this;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Cfisica productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Cfisica addProducto(Producto producto) {
        this.productos.add(producto);
        producto.getCfisicas().add(this);
        return this;
    }

    public Cfisica removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.getCfisicas().remove(this);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cfisica cfisica = (Cfisica) o;
        if (cfisica.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cfisica.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cfisica{" +
            "id=" + getId() +
            ", importe=" + getImporte() +
            ", fecha='" + getFecha() + "'" +
            ", codigo=" + getCodigo() +
            "}";
    }
}
