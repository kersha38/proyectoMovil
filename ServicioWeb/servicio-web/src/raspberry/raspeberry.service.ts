import {Injectable} from "@nestjs/common";

@Injectable()
export class RaspeberryService {
    ordenes:Orden[]=[];
    sensados:Senso[]=[];
    raspberryPublicada="";
    raspInfos:RaspInfo[]=[];

    anadirOrden(raspberry,tipo){

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

        return {"mensaje":"ordenExitosa"};
    }

    obtenerOrden(raspberry){
        let orden:any;
        let indice=0;
        this.ordenes.forEach((value,i)=>{
            if (value.raspberry==raspberry){
                orden=value;
                indice=i;
            }
        });
        if(orden){
            //this.ordenes.pop()
            this.ordenes.splice(indice,1);
            return orden;
        }

        return {};
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

    publicarRaspberry(raspberry){
        this.raspberryPublicada=raspberry;
        return raspberry;
    }

    getRaspberry(){
        return this.raspberryPublicada;
    }

    publicarIP(raspberry,ipPura){
        console.log("ipPura: "+ipPura);
        let actualizo=false;
        const raspInfo= new RaspInfo(raspberry,ipPura);
        this.raspInfos.forEach((value)=>{
            if(value.raspberry==raspberry){

                value.ipPura=ipPura;//sin substringla cadena completa OJO
                value.ipPura=ipPura.substring(7);
                actualizo=true;
                return value;

            }
        });

        if(!actualizo){
            this.raspInfos.push(raspInfo);
        }

        return raspInfo;
    }

    consultarIP(raspberry){
        let raspInfo:any;
        this.raspInfos.forEach((value)=>{
            if (value.raspberry==raspberry){
                raspInfo=value;
            }
        });

        return raspInfo;
        //return this.sensados;
    }
}
export class Orden {
    constructor(public raspberry,public tipo){}

}

export class Senso {
    constructor(public raspberry,public estado){}

}

export class RaspInfo {
    constructor(public raspberry,public ipPura){}

}