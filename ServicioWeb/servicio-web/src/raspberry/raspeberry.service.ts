import {Injectable} from "@nestjs/common";

@Injectable()
export class RaspeberryService {
    ordenActual:Orden;

    ordenToJSON(){
        return const ordenJSON={type:this.ordenActual.type,target:this.ordenActual.target}
    }

    eliminarOrden(){

    }


}

export class Orden{
    constructor(public type,public target){

    }
}