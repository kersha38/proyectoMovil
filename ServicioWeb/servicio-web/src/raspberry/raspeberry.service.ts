import {Injectable} from "@nestjs/common";

@Injectable()
export class RaspeberryService {
    ordenes:any={};
    sensados:any={};

    añadirOrden(raspberry,tipo){
        this.ordenes[raspberry].tipo=tipo;

        return this.ordenes[raspberry];
    }

    obtenerOrden(raspberry){
        const orden= this.ordenes[raspberry];
        delete this.ordenes[raspberry];
        return orden;
    }


    consultarSenso(raspberry){
        return this.sensados[raspberry];
    }

    actualizarSenso(raspberry,estado){
        this.sensados[raspberry].estado=estado;
        return this.sensados[raspberry];
    }

}
