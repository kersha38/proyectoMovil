import {Injectable} from "@nestjs/common";

@Injectable()
export class RaspeberryService {
    ordenes:Orden[]=[];
    sensados:Senso[]=[];

    añadirOrden(raspberry,tipo){

        var actualizo=false;
        const orden= new Orden(raspberry,tipo);
        this.ordenes.map((value)=>{
           if(value.raspberry==raspberry){

               value.tipo=tipo;
               return value;
               actualizo=true;
           }
        });

        if(!actualizo){
            this.ordenes.push(orden)
        }

        return orden;
    }

    obtenerOrden(raspberry){
        var orden:any;
        this.ordenes.forEach((value)=>{
            if (value.raspberry==raspberry){
                orden==value;
            }
        });

        return orden;
    }


    consultarSenso(raspberry){
        var senso:any;
        this.sensados.forEach((value)=>{
            if (value.raspberry==raspberry){
                senso==value;
            }
        });

        return senso;
    }

    actualizarSenso(raspberry,estado){
        var actualizo=false;
        const senso= new Senso(raspberry,estado);
        this.sensados.map((value)=>{
            if(value.raspberry==raspberry){

                value.estado=estado;
                return value;
                actualizo=true;
            }
        });

        if(!actualizo){
            this.sensados.push(senso);
        }

        return senso;
    }

}
export class Orden {
    constructor(public raspberry,public tipo){}

}

export class Senso {
    constructor(public raspberry,public estado){}

}