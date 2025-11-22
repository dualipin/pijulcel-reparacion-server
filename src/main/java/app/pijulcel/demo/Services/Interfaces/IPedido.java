package app.pijulcel.demo.Services.Interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.DTOs.PedidoPostDTO;
import app.pijulcel.demo.Models.Pedido;

public interface IPedido {

    ApiResponse<Pedido> registerPedido(PedidoPostDTO pedido, List<MultipartFile> imagenes, MultipartFile audio);

    ApiResponse<List<Pedido>> getPedidos();

    ApiResponse<Void> updateEstatusPedido(Integer id, String estatus);

    ApiResponse<Pedido> getPedidoById(Integer id);

    ApiResponse<Void> deletePedido(Integer id);
}