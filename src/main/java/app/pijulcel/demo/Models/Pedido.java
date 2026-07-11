package app.pijulcel.demo.Models;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pedidos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "bar_code", nullable = false)
    private Integer barCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disp_id", nullable = false)
    private Dispositivo dispositivo;

    @Column(name = "descrip", nullable = true, length = 255)
    private String descrip;

    @Column(name = "audio", nullable = true, length = 255)
    private String audio;

    // @Column(name = "video", nullable = true, length = 255)
    // private String video;

    @Column(name = "img_1", nullable = true, length = 255)
    private String img1;

    @Column(name = "img_2", nullable = true, length = 255)
    private String img2;

    @Column(name = "img_3", nullable = true, length = 255)
    private String img3;

    @Column(name = "img_4", nullable = true, length = 255)
    private String img4;

    @Column(name = "estatus", nullable = false)
    private String estatus = "Pendiente";

    @Column(name = "delivery_at", nullable = false)
    private LocalDateTime deliveryAt;

    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated = LocalDateTime.now();

}
