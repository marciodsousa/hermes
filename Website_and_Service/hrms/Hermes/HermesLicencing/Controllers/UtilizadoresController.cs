﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using System.Data.Entity.Validation;
using HermesLicencing.Models;

namespace HermesLicencing.Controllers
{
    public class UtilizadoresController : ApplicationController
    {
        //
        // GET: /Utilizadores/

        Models.LicencingDBEntities db = new Models.LicencingDBEntities();

        public ActionResult Index()
        {
            return Json(Models.TUtilizador.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int id)
        {
            var user = Models.TUtilizador.GetById(id);
            if (user == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                return Json(user, JsonRequestBehavior.AllowGet);
            }
        }

        public ActionResult Update(int id, string usrname, string pswdslt, string name, int idE, int idTU, int stat, string serial, string pwd, string email)
        {
            ActionResult result = null;

            TUtilizador user = new TUtilizador { idUtilizador = id,
                                                 username = usrname,
                                                 passwordSalt = pswdslt,
                                                 nome = name,
                                                 idEmpresa = idE,
                                                 idTipoUtilizador = idTU,
                                                 estado = stat,
                                                 numSerieEquip = serial,
                                                 password = pwd,
                                                 email = email};
            switch (TUtilizador.Update(user))
            {
                case 1: //utilizador actualizado com sucesso
                    result = Json(TUtilizador.GetById(id));
                    break;

                case 0: //utilizador não encontrado
                    result = new HttpNotFoundResult();
                    break;

                case 2: //dados inválidos
                    result = new HttpStatusCodeResult(400);
                    break;

                case 3: 
                    result = new HttpStatusCodeResult(409);
                    break;
            }

            return result;
        }

        public ActionResult Create(TUtilizador user)
        {
            int userId;
            userId = TUtilizador.AddUser(user);
            return Json(TUtilizador.GetById(userId));
        }

        [HttpGet]
        public ActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Login(Models.TUtilizador user)
        {

            //if (ModelState.IsValid)
            //{
            if (IsValid(user.username, user.password)>0)
            {
                Session["user"] = db.TUtilizador.FirstOrDefault(u => u.username == user.username);
                return RedirectToAction("Index", "Home");
            }
            else
            {
                ModelState.AddModelError("", "Login data is incorrect!");
            }
            //}
            //var errors = ModelState.Values.SelectMany(v => v.Errors);
            return View(user);
        }
        public ActionResult Logout()
        {
            FormsAuthentication.SignOut();
            return RedirectToAction("Index", "Home");
        }


        public static int IsValid(string _username, string _password)
        {
            var crypto = new SimpleCrypto.PBKDF2();

            int isValid = 0;

            using (var db = new Models.LicencingDBEntities())
            {
                var utilizador = db.TUtilizador.FirstOrDefault(u => u.username == _username);
                if (utilizador != null)
                {
                    if (utilizador.password == crypto.Compute(_password, utilizador.passwordSalt))
                    {
                        isValid = utilizador.idUtilizador;
                    }
                    else
                    {
                        isValid = -1;
                    }
                }
                else
                {
                    isValid = 0;
                }
                return isValid;
            }
        }
    }
}