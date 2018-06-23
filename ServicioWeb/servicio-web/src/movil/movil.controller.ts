import {Body, Controller, Post} from "@nestjs/common";
import {RaspeberryService} from "../raspberry/raspeberry.service";

@Controller('Movil')
export class MovilController {

    constructor(private _raspberryService:RaspeberryService){

    }
    @Post('anadirOrden')
    añadirOrden(@Body('tipo') tipo,@Body('raspberry') raspberry){
        return this._raspberryService.añadirOrden(raspberry,tipo);

    }

    @Post('consultarSenso')
    consultarSenso(@Body('raspberry') raspberry){
        return this._raspberryService.consultarSenso(raspberry);
    }

}