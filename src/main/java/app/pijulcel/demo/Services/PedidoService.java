 package app.pijulcel.demo.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.pijulcel.demo.DTOs.ApiResponse;
import app.pijulcel.demo.DTOs.PedidoPostDTO;
import app.pijulcel.demo.Models.Cliente;
import app.pijulcel.demo.Models.Dispositivo;
import app.pijulcel.demo.Models.Pedido;
import app.pijulcel.demo.Repositories.PedidoRepository;
import app.pijulcel.demo.Services.Interfaces.IPedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class PedidoService implements IPedido {

    @Autowired
    private DispositivoService dispService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private StorageService storageService;
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public ApiResponse<Pedido> registerPedido(
            PedidoPostDTO pedido,
            List<MultipartFile> imagenes,
            MultipartFile audio,
            MultipartFile video) {

        // Registrar al cliente
        ApiResponse<Cliente> cliRes = clienteService.register(pedido.getCliente());
        if (!cliRes.isSuccess()) {
            return new ApiResponse<>(false, "Error al registrar cliente.", null);
        }

        // Registrar el dispositivo
        ApiResponse<Dispositivo> dispRes = dispService.register(pedido.getDispositivo());
        if (!dispRes.isSuccess()) {
            return new ApiResponse<>(false, "Error al registrar dispositivo.", null);
        }

        // Crear el pedido
        Pedido pedidoEntity = new Pedido();
        pedidoEntity.setBarCode(pedido.getBarCode());
        pedidoEntity.setCliente(cliRes.getData());
        pedidoEntity.setDispositivo(dispRes.getData());
        
        if(pedido.getDescrip() != null)
            pedidoEntity.setDescrip(pedido.getDescrip());

        pedidoEntity.setDeliveryAt(pedido.getDeliveryAt());

        // GUARDAR IMÁGENES — SOLO SI LLEGAN
        if (imagenes.get(0) != null && !imagenes.get(0).isEmpty()) {
            String img1FileName = storageService.generateFileName();
            pedidoEntity.setImg1("pedidos/"+img1FileName);
            storageService.store(imagenes.get(0), "pedidos/", img1FileName);
        }
        if (imagenes.size() > 1) {
            if (imagenes.get(1) != null && !imagenes.get(1).isEmpty()) {
                String img2FileName = storageService.generateFileName();
                pedidoEntity.setImg2("pedidos/"+img2FileName);
                storageService.store(imagenes.get(1), "pedidos/", img2FileName);
            }
        }
        if (imagenes.size() > 2) {
            if (imagenes.get(2) != null && !imagenes.get(2).isEmpty()) {
                String img3FileName = storageService.generateFileName();
                pedidoEntity.setImg3("pedidos/"+img3FileName);
                storageService.store(imagenes.get(2), "pedidos/", img3FileName);
            }
        }
        if (imagenes.size() > 3) {
            if (imagenes.get(3) != null && !imagenes.get(3).isEmpty()) {
                String img4FileName = storageService.generateFileName();
                pedidoEntity.setImg4("pedidos/"+img4FileName);
                storageService.store(imagenes.get(3), "pedidos/", img4FileName);
            }
        }

        // Guardar el pedido en la base de datos
        em.persist(pedidoEntity);

        // GUARDAR AUDIO — SOLO SI LLEGA
        if (audio != null && !audio.isEmpty()) {
            String audioFileName = storageService.generateFileAudio();
            pedidoEntity.setAudio("audios/"+audioFileName);
            storageService.saveAudio(audio, audioFileName);
        }

        // GUARDAR VIDEO — SOLO SI LLEGA
        // if (video != null && !video.isEmpty()) {
        //     String videoFileName = storageService.generateFileVideo();
        //     pedidoEntity.setVideo("videos/"+videoFileName);
        //     storageService.saveVideo(video, videoFileName);
        // }

        return new ApiResponse<>(true, "Pedido registrado con éxito.", pedidoEntity);
    }

    @Override
    @Transactional
    public ApiResponse<Void> updateEstatusPedido(Integer id, String estatus) {
        Integer updatedCount = pedidoRepository.updateEstatusPedido(id, estatus);
        
        if (updatedCount == 0)
            return new ApiResponse<>(false, "Pedido no encontrado o estatus no actualizado.", null);
        
        return new ApiResponse<Void>(true, "Estatus del pedido actualizado con éxito.", null);
    }

    @Override
    public ApiResponse<List<Pedido>> getPedidos() {
        List<Pedido> pedidos = em.createQuery("SELECT p FROM Pedido p", Pedido.class).getResultList();
        return new ApiResponse<>(true, "Pedidos obtenidos con éxito.", pedidos);
    }

    @Override
    public ApiResponse<Pedido> getPedidoById(Integer id) {
        Pedido pedido = em.find(Pedido.class, id);
        if (pedido == null) {
            return new ApiResponse<>(false, "Pedido no encontrado.", null);
        }
        return new ApiResponse<>(true, "Pedido obtenido con éxito.", pedido);
    }

    @Override
    @Transactional
    public ApiResponse<Void> deletePedido(Integer id) {
        Pedido pedido = em.find(Pedido.class, id);
        if (pedido == null) {
            return new ApiResponse<>(false, "Pedido no encontrado.", null);
        }
        em.remove(pedido);
        if(pedido.getImg1() != null)
            storageService.delete(pedido.getImg1());
        if(pedido.getImg2() != null)
            storageService.delete(pedido.getImg2());
        if(pedido.getImg3() != null)
            storageService.delete(pedido.getImg3());
        if(pedido.getImg4() != null)
            storageService.delete(pedido.getImg4());
        if(pedido.getAudio() != null)
            storageService.delete(pedido.getAudio());
        // if(pedido.getVideo() != null)
        //     storageService.delete(pedido.getVideo());

        this.dispService.delete(pedido.getDispositivo().getId());
        this.clienteService.delete(pedido.getCliente().getId());
        
        em.flush();
        return new ApiResponse<>(true, "Pedido eliminado con éxito.", null);
    }

}