/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.cava.examples.security.Seguridad.service;

import com.cava.examples.common.entitis.Usuario;

/**
 *
 * @author cesar
 */
public interface UsuarioService {

    Usuario userfindByName(String userName);

    Usuario saveuser(Usuario user);
}
