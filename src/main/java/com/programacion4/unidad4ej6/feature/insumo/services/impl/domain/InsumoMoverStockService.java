package com.programacion4.unidad4ej6.feature.insumo.services.impl.domain;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.programacion4.unidad4ej6.feature.insumo.services.interfaces.domain.IInsumoMoverStockService;
import com.programacion4.unidad4ej6.config.exceptions.BadRequestException;
import com.programacion4.unidad4ej6.feature.insumo.dtos.request.MovimientoStockDTO;
import com.programacion4.unidad4ej6.feature.insumo.dtos.response.MovimientoStockResponseDTO;

import com.programacion4.unidad4ej6.feature.insumo.services.interfaces.commons.IInsumoFindByIdService;
import com.programacion4.unidad4ej6.feature.insumo.repositories.IInsumoRepository;
import com.programacion4.unidad4ej6.feature.insumo.models.Insumo;
import com.programacion4.unidad4ej6.feature.insumo.mappers.MovimientoStockMapper;
import com.programacion4.unidad4ej6.feature.insumo.models.TipoMovimiento;

@AllArgsConstructor
public class InsumoMoverStockService implements IInsumoMoverStockService {

    private IInsumoFindByIdService insumoFindByIdService;

    private IInsumoRepository insumoRepository;

    @Override
    public MovimientoStockResponseDTO moverStock(Long id, MovimientoStockDTO dto) {

        Insumo insumo = insumoFindByIdService.findByIdAndActivoTrue(id);

        if (dto.getTipoMovimiento() == TipoMovimiento.ENTRADA) {
            insumo.setStockActual(insumo.getStockActual() + dto.getCantidad());

        } else if (dto.getTipoMovimiento() == TipoMovimiento.SALIDA) {

            if (insumo.getStockActual() < dto.getCantidad()) {
                throw new BadRequestException("No hay suficiente stock");
            }

            insumo.setStockActual(insumo.getStockActual() - dto.getCantidad());
        } else {
            throw new BadRequestException("No hay suficiente stock para realizar la salida");
        }

        insumo.getMovimientosStock().add(MovimientoStockMapper.toEntity(dto, insumo));

        insumoRepository.save(insumo);

        return MovimientoStockMapper.toResponseDTO(
            insumo.getMovimientosStock().get(insumo.getMovimientosStock().size() - 1)
        );
    }
}
