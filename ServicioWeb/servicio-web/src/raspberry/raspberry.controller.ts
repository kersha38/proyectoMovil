import {
    Controller,
    FileInterceptor,
    Get,
    Post,
    Query, Req,
    Res,
    UploadedFile,
    UseInterceptors
} from "@nestjs/common";
import {RaspeberryService} from "./raspeberry.service";

// opciones del middleware multer para subir archivos
const multer = require('multer');
const storage = multer.diskStorage({
    destination: function(req, file, callback) {
        callback(null, './subidos')
    },
    filename: function(req, file, callback) {
        callback(null, file.originalname)
    }
});
@Controller('Raspberry')
export class RaspberryController {
    constructor(private _raspberryService:RaspeberryService){

    }

    @Get('actualizarSenso')
    actualizarSenso(@Query('raspberry') raspberry,
                    @Query('estado') estado){
        return this._raspberryService.actualizarSenso(raspberry,estado);
    }

    @Get('obtenerOrden')
    obtenerOrden(@Query('raspberry') raspberry){
        return this._raspberryService.obtenerOrden(raspberry);
    }

    @Get('obtenerRaspberry')
    obtenerRaspberry(@Res() response){
        //const raspberry=request.ip;
        console.log("idRaspberry: "+this._raspberryService.getRaspberry());
        response.redirect('http://api.qrserver.com/v1/create-qr-code/?data='+this._raspberryService.getRaspberry()+'&size=100x100');
    }

    @Get('publicarRaspberry')
    publicarRaspberry(@Query('raspberry') raspberry){
        return this._raspberryService.publicarRaspberry(raspberry);
    }

    @Post('subirVideo')
    @UseInterceptors(FileInterceptor('file',
        {
            //dest:'subidos/',
            storage:storage
        }))
    uploadFile(@UploadedFile() file) {
        console.log("se subio un archivo".toLocaleUpperCase());
        console.log(file);
    }

    @Get('publicarIP')
    publicarIP(@Query('raspberry')raspberry,@Req() req){
        return this._raspberryService.publicarIP(raspberry,req.ip);

    }

    @Get('consultarIP')
    consultarIP(@Query('raspberry') raspberry){
        return this._raspberryService.consultarIP(raspberry);
    }

    @Get('consultarConfiguracion')
    consultarConfiguracion(@Query('raspberry') raspberry){
        return this._raspberryService.consultarConfiguracion(raspberry);
    }

    @Get('cambiarConfiguracion')
    cambiarConfiguracion(@Query('raspberry') raspberry,
                         @Query('cantidadAgua') cantidadAgua,
                         @Query('cantidadComida') cantidadComida){

        return this._raspberryService.cambiarConfiguracion(raspberry,cantidadAgua,cantidadComida);
    }
}