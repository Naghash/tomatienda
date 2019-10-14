package com.tomatienda.myapp.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Conline.
 */
@Entity
@Table(name = "conline")
public class Conline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "importe")
    private Integer importe;

    @Column(name = "fecha")
    private Instant fecha;

    @Column(name = "descuento")
    private Boolean descuento;

    @Column(name = "codigo")
    private Integer codigo;

    @ManyToOne
    private Cliente cliente;

    @ManyToMany
    @JoinTable(name = "conline_producto",
               joinColumns = @JoinColumn(name="conlines_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="productos_id", referencedColumnName="id"))
    private Set<Producto> productos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getImporte() {
        return importe;
    }

    public Conline importe(Integer importe) {
        this.importe = importe;
        return this;
    }

    public void setImporte(Integer importe) {
        this.importe = importe;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Conline fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public Boolean isDescuento() {
        return descuento;
    }

    public Conline descuento(Boolean descuento) {
        this.descuento = descuento;
        return this;
    }

    public void setDescuento(Boolean descuento) {
        this.descuento = descuento;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Conline codigo(Integer codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Conline cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public Conline productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public Conline addProducto(Producto producto) {
        this.productos.add(producto);
        producto.getConlines().add(this);
        return this;
    }

    public Conline removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.getConlines().remove(this);
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
        Conline conline = (Conline) o;
        if (conline.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), conline.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Conline{" +
            "id=" + getId() +
            ", importe=" + getImporte() +
            ", fecha='" + getFecha() + "'" +
            ", descuento='" + isDescuento() + "'" +
            ", codigo=" + getCodigo() +
            "}";
    }
}
