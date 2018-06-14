import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import {UsuarioController} from "./movil/usuario.controller";
import {UsuarioService} from "./movil/usuario.service";

@Module({
  imports: [],
  controllers: [AppController, UsuarioController],
  providers: [AppService, UsuarioService]
})
export class AppModule {}
