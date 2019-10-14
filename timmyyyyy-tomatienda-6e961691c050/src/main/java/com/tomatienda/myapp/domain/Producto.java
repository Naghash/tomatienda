package com.tomatienda.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Min(value = 0)
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @NotNull
    @Column(name = "caducidad", nullable = false)
    private Instant caducidad;

    @NotNull
    @Column(name = "seccion", nullable = false)
    private String seccion;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "pvp", nullable = false)
    private Double pvp;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "pcompra", nullable = false)
    private Double pcompra;

    @NotNull
    @Min(value = -1)
    @Column(name = "minstock", nullable = false)
    private Integer minstock;

    @NotNull
    @Min(value = 1)
    @Column(name = "lote", nullable = false)
    private Integer lote;

    @NotNull
    @Column(name = "foto", nullable = false)
    private String foto;

    @ManyToMany(mappedBy = "productos")
    @JsonIgnore
    private Set<Conline> conlines = new HashSet<>();

    @ManyToMany(mappedBy = "productos")
    @JsonIgnore
    private Set<Cfisica> cfisicas = new HashSet<>();

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

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public Producto stock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Instant getCaducidad() {
        return caducidad;
    }

    public Producto caducidad(Instant caducidad) {
        this.caducidad = caducidad;
        return this;
    }

    public void setCaducidad(Instant caducidad) {
        this.caducidad = caducidad;
    }

    public String getSeccion() {
        return seccion;
    }

    public Producto seccion(String seccion) {
        this.seccion = seccion;
        return this;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPvp() {
        return pvp;
    }

    public Producto pvp(Double pvp) {
        this.pvp = pvp;
        return this;
    }

    public void setPvp(Double pvp) {
        this.pvp = pvp;
    }

    public Double getPcompra() {
        return pcompra;
    }

    public Producto pcompra(Double pcompra) {
        this.pcompra = pcompra;
        return this;
    }

    public void setPcompra(Double pcompra) {
        this.pcompra = pcompra;
    }

    public Integer getMinstock() {
        return minstock;
    }

    public Producto minstock(Integer minstock) {
        this.minstock = minstock;
        return this;
    }

    public void setMinstock(Integer minstock) {
        this.minstock = minstock;
    }

    public Integer getLote() {
        return lote;
    }

    public Producto lote(Integer lote) {
        this.lote = lote;
        return this;
    }

    public void setLote(Integer lote) {
        this.lote = lote;
    }

    public String getFoto() {
        return foto;
    }

    public Producto foto(String foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Set<Conline> getConlines() {
        return conlines;
    }

    public Producto conlines(Set<Conline> conlines) {
        this.conlines = conlines;
        return this;
    }

    public Producto addConline(Conline conline) {
        this.conlines.add(conline);
        conline.getProductos().add(this);
        return this;
    }

    public Producto removeConline(Conline conline) {
        this.conlines.remove(conline);
        conline.getProductos().remove(this);
        return this;
    }

    public void setConlines(Set<Conline> conlines) {
        this.conlines = conlines;
    }

    public Set<Cfisica> getCfisicas() {
        return cfisicas;
    }

    public Producto cfisicas(Set<Cfisica> cfisicas) {
        this.cfisicas = cfisicas;
        return this;
    }

    public Producto addCfisica(Cfisica cfisica) {
        this.cfisicas.add(cfisica);
        cfisica.getProductos().add(this);
        return this;
    }

    public Producto removeCfisica(Cfisica cfisica) {
        this.cfisicas.remove(cfisica);
        cfisica.getProductos().remove(this);
        return this;
    }

    public void setCfisicas(Set<Cfisica> cfisicas) {
        this.cfisicas = cfisicas;
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
        Producto producto = (Producto) o;
        if (producto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), producto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", stock=" + getStock() +
            ", caducidad='" + getCaducidad() + "'" +
            ", seccion='" + getSeccion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", pvp=" + getPvp() +
            ", pcompra=" + getPcompra() +
            ", minstock=" + getMinstock() +
            ", lote=" + getLote() +
            ", foto='" + getFoto() + "'" +
            "}";
    }
}
