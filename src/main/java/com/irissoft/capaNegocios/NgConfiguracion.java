
package com.irissoft.capaNegocios;

import com.irissoft.capaAccesoDatos.QConfiguracion;
import com.irissoft.datos.DtConfiguracion;

public class NgConfiguracion {

    private final QConfiguracion qConfiguracion = new QConfiguracion();

    public void actualizarConfiguracion(String nombreTienda, String ruc, String direccion, String telefono) {
        DtConfiguracion config = new DtConfiguracion();
        config.setNombreTienda(nombreTienda);
        config.setRuc(ruc);
        config.setDireccion(direccion);
        config.setTelefono(telefono);

        qConfiguracion.actualizarConfiguracion(config);
    }

    public DtConfiguracion obtenerConfiguracion() {
        return qConfiguracion.obtenerConfiguracion();
    }

}
