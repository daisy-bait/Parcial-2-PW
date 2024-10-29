/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usco.parcialPW.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "veh_id")
    private Long id;

    @NotNull
    @NotEmpty(message = "No puede estar vacía")
    @Pattern(regexp = "^[A-Z]{3}-\\d{3}$", message = "La placa debe seguir la forma XXX-000, 3 Letras y 3 números separados por un guión")
    @Column(name = "veh_placa")
    private String placa;

    @NotNull
    @Min(1)
    @Max(24)
    @Column(name = "veh_entrada")
    private Integer entrada;
    
    @Min(1)
    @Max(24)
    @Column(name = "veh_salida")
    private Integer salida;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-]+$", message = "Sólo valores alfanuméricos")
    @Column(name = "veh_ubicacion")
    private String ubicacion;

    @Valid
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private Type type;

}