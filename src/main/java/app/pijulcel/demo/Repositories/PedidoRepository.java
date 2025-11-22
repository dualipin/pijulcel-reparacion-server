package app.pijulcel.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.pijulcel.demo.Models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Modifying
    @Query("UPDATE Pedido p SET p.estatus = :estatus WHERE p.id = :id")
    Integer updateEstatusPedido(@Param("id") Integer id, @Param("estatus") String estatus);

}
