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
 * A Empleado.
 */
@Entity
@Table(name = "empleado")
public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido", nullable = false)
    private String apellido;

    @NotNull
    @Pattern(regexp = "^[0-9]{8,8}[A-Za-z]$")
    @Column(name = "dni", nullable = false)
    private String dni;

    @NotNull
    @Column(name = "fechanac", nullable = false)
    private Instant fechanac;

    @NotNull
    @Column(name = "ingreso", nullable = false)
    private Instant ingreso;

    @NotNull
    @Column(name = "sueldo", nullable = false)
    private Integer sueldo;

    @NotNull
    @Column(name = "cargo", nullable = false)
    private String cargo;

    @OneToMany(mappedBy = "empleado")
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

    public Empleado nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Empleado apellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public Empleado dni(String dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Instant getFechanac() {
        return fechanac;
    }

    public Empleado fechanac(Instant fechanac) {
        this.fechanac = fechanac;
        return this;
    }

    public void setFechanac(Instant fechanac) {
        this.fechanac = fechanac;
    }

    public Instant getIngreso() {
        return ingreso;
    }

    public Empleado ingreso(Instant ingreso) {
        this.ingreso = ingreso;
        return this;
    }

    public void setIngreso(Instant ingreso) {
        this.ingreso = ingreso;
    }

    public Integer getSueldo() {
        return sueldo;
    }

    public Empleado sueldo(Integer sueldo) {
        this.sueldo = sueldo;
        return this;
    }

    public void setSueldo(Integer sueldo) {
        this.sueldo = sueldo;
    }

    public String getCargo() {
        return cargo;
    }

    public Empleado cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Set<Cfisica> getCfisicas() {
        return cfisicas;
    }

    public Empleado cfisicas(Set<Cfisica> cfisicas) {
        this.cfisicas = cfisicas;
        return this;
    }

    public Empleado addCfisica(Cfisica cfisica) {
        this.cfisicas.add(cfisica);
        cfisica.setEmpleado(this);
        return this;
    }

    public Empleado removeCfisica(Cfisica cfisica) {
        this.cfisicas.remove(cfisica);
        cfisica.setEmpleado(null);
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
        Empleado empleado = (Empleado) o;
        if (empleado.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), empleado.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Empleado{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido='" + getApellido() + "'" +
            ", dni='" + getDni() + "'" +
            ", fechanac='" + getFechanac() + "'" +
            ", ingreso='" + getIngreso() + "'" +
            ", sueldo=" + getSueldo() +
            ", cargo='" + getCargo() + "'" +
            "}";
    }
}
