package com.programacion4.unidad4ej6.feature.insumo.services.interfaces.domain;

import com.programacion4.unidad4ej6.feature.insumo.dtos.request.MovimientoStockDTO;
import com.programacion4.unidad4ej6.feature.insumo.dtos.response.MovimientoStockResponseDTO;

public interface IInsumoRecoveryService {
    void recoveryInsumo(Long id);
}
