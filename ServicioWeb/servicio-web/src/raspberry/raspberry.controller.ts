import {Body, Controller, Post} from "@nestjs/common";
import {RaspeberryService} from "./raspeberry.service";

@Controller()
export class RaspberryController {
    constructor(private _raspberryService:RaspeberryService){

    }

    @Post('actualizarSenso')
    actualizarSenso(@Body('raspberry') raspberry,@Body('estado') estado){
        return this._raspberryService.actualizarSenso(raspberry,estado);
    }

    @Post('obtenerOrden')
    obtenerOrden(@Body('raspberry') raspberry){
        return this._raspberryService.obtenerOrden(raspberry);
    }

    @Post('obtenerRaspberry')
    obtenerRaspberry(){
        
    }
}