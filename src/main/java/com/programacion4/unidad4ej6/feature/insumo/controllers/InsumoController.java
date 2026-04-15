package com.programacion4.unidad4ej6.feature.insumo.controllers;

import com.programacion4.unidad4ej6.config.BaseResponse;
import com.programacion4.unidad4ej6.feature.insumo.dtos.request.InsumoCreateDTO;
import com.programacion4.unidad4ej6.feature.insumo.dtos.request.MovimientoStockDTO;
import com.programacion4.unidad4ej6.feature.insumo.dtos.request.PrecioUpdateDTO;
import com.programacion4.unidad4ej6.feature.insumo.dtos.response.InsumoResponseDTO;
import com.programacion4.unidad4ej6.feature.insumo.dtos.response.MovimientoStockResponseDTO;
import com.programacion4.unidad4ej6.feature.insumo.services.impl.domain.InsumoRecoveryService;
import com.programacion4.unidad4ej6.feature.insumo.services.interfaces.domain.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/insumos")
@AllArgsConstructor
public class InsumoController {

    private final IInsumoCreateService insumoCreateService;

    @PostMapping
    public ResponseEntity<BaseResponse<InsumoResponseDTO>> createInsumo(
            @Valid @RequestBody InsumoCreateDTO dto
    ) {
        InsumoResponseDTO insumoResponseDTO = insumoCreateService.createInsumo(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        BaseResponse.ok(
                                insumoResponseDTO,
                                "Insumo creado correctamente"
                        )
                );
    }

    private final IInsumoGetService insumoGetService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<InsumoResponseDTO>> getInsumo(
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.ok(
                        insumoGetService.getInsumo(id),
                        "Insumo encontrado correctamente"
                ));
    }

    private final IInsumoDeleteService insumoDeleteService;

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<InsumoResponseDTO>> deleteInsumo(
            @PathVariable Long id
    ) {
        insumoDeleteService.deleteInsumo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(BaseResponse.ok(
                        null,
                        "Insumo eliminado correctamente"
                ));
    }

    private final IInsumoUpdatePrecioService insumoUpdatePrecioService;

    @PatchMapping("/{id}/precio")
    public ResponseEntity<BaseResponse<InsumoResponseDTO>> updatePrecio(
            @PathVariable Long id,
            @Valid @RequestBody PrecioUpdateDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        BaseResponse.ok(
                                insumoUpdatePrecioService.updatePrecio(id, dto),
                                "Precio actualizado correctamente"
                        )
                );
    }

    private final IInsumoMoverStockService insumoMoverStockService;

    @PostMapping("/{id}/stock")
    public ResponseEntity<BaseResponse<MovimientoStockResponseDTO>> moverStock(
            @PathVariable Long id,
            @Valid @RequestBody MovimientoStockDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.ok(
                                insumoMoverStockService.moverStock(id, dto),
                                "Movimiento de stock realizado correctamente"
                        )
                );
    }

    private final InsumoRecoveryService insumoRecoveryService;

    @PutMapping("/{id}/recovery")
    public ResponseEntity<BaseResponse<Void>> recoveryInsumo(
            @PathVariable Long id
    ) {
        insumoRecoveryService.recoveryInsumo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(BaseResponse.ok(
                        null,
                        "Insumo recuperado correctamente"
                ));
    }
}
