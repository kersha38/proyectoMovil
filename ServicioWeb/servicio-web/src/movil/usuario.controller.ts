import {Body, Controller, Get, Post, Query, Res} from "@nestjs/common";
import {UsuarioService} from "./usuario.service";
import {RaspeberryService} from "../raspberry/raspeberry.service";
const fs = require('fs');
@Controller('Usuario')
export class UsuarioController {
    constructor(private _usuarioService: UsuarioService) {
        _usuarioService.listarUsuarios();
    }

    @Get('autentificarComun')
    autentificarComun(@Query('nickname') nickname,@Query('password') password){
        return this._usuarioService.autentificarComun(nickname,password);
    }

    @Get('autentificarGmailFb')
    autentificarGmailFb(@Query('mail') mail){
        return this._usuarioService.autentificarGmailFb(mail);
    }

    @Get('existeGmailFb')
    existeGmailFb(@Query('mail') mail){
        return this._usuarioService.existeGmailFb(mail);
    }

    @Post('crearComun')
    crearComun(@Body() usuario
               // @Body('nickname') nickname,
               // @Body('mail') mail,
               // @Body('password') password
    ) {
        return this._usuarioService.crearUsuarioComun(usuario.nickname, usuario.mail, usuario.password);
    }

    @Post('crearConGmailFb')
    crearConGmailFb(@Body() usuario
                    // @Body('nickname') nickname,
                    // @Body('mail') mail,
    ) {
        return this._usuarioService.crearConGmailFb(usuario.nickname, usuario.mail);
    }

    @Get('listar')
    listar() {
        return this._usuarioService.listarUsuarios();
    }

    @Post('obtener')
    obtenerUsuario(@Body('nickname') nickname) {
        return this._usuarioService.obtenerUsuario(nickname);
    }

    @Post('registrarRaspberry')
    registrarRaspberry(@Body() usuario){
        return this._usuarioService.registrarRaspberry(usuario.ipRasp,usuario.mail);
    }

    @Get('obtenerVideo')
    obtenerVideo(@Res() res){
        const path = 'subidos/sample.mp4';
        const stat = fs.statSync(path);
        const fileSize = stat.size;
        const head = {
            'Content-Length': fileSize,
            'Content-Type': 'video/mp4',
        };
        res.writeHead(200, head);
        fs.createReadStream(path).pipe(res);
    }
}