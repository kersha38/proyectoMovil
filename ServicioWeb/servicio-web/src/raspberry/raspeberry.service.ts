import {Injectable} from "@nestjs/common";

@Injectable()
export class RaspeberryService {
    ordenes:Orden[]=[];
    sensados:Senso[]=[];

    añadirOrden(raspberry,tipo){

        let actualizo=false;
        const orden= new Orden(raspberry,tipo);
        this.ordenes.map((value)=>{
           if(value.raspberry==raspberry){

               value.tipo=tipo;
               actualizo=true;
               return value;

           }
        });

        if(!actualizo){
            this.ordenes.push(orden)
        }

        return orden;
    }

    obtenerOrden(raspberry){
        let orden:any;
        this.ordenes.forEach((value)=>{
            if (value.raspberry==raspberry){
                orden=value;
            }
        });

        return orden;
    }


    consultarSenso(raspberry){
        let senso:any;
        this.sensados.forEach((value)=>{
            if (value.raspberry==raspberry){
                senso=value;
            }
        });

        return senso;
        //return this.sensados;
    }

    actualizarSenso(raspberry,estado){
        let actualizo=false;
        const senso= new Senso(raspberry,estado);
        this.sensados.forEach((value)=>{
            if(value.raspberry==raspberry){

                value.estado=estado;
                actualizo=true;
                return value;

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