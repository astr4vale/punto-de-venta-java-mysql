
package com.irissoft.capaNegocios;

import com.irissoft.capaAccesoDatos.QReportes;
import com.irissoft.datos.DtReportes;
import java.util.List;

public class NgReportes {

    private final QReportes qReportes = new QReportes();

    public List<DtReportes> obtenerVentas() {
        return qReportes.obtenerVentas();
    }

}
