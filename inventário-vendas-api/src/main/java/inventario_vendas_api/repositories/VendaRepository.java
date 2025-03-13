package inventario_vendas_api.repositories;

import inventario_vendas_api.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query("SELECT COALESCE(SUM(v.valorTotal), 0) FROM Venda v " +
            "WHERE v.cliente.id = :clienteId AND v.dataVenda > :dataFechamento")
    BigDecimal calcularTotalComprasAposFechamento(
            @Param("clienteId") Long clienteId,
            @Param("dataFechamento") LocalDate dataFechamento
    );

}



