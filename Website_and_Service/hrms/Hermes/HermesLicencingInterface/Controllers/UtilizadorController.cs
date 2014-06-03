using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.Mvc;
using HermesLicencingInterface.Models;

namespace HermesLicencingInterface.Controllers
{
    public class UtilizadorController : Controller
    {
        //
        // GET: /Utilizador/

        public ActionResult Index()
        {
            return View();
        }

        [HttpGet]
        public ActionResult Login()
        {
            return View();
        }

        [HttpPost]
        public ActionResult Login(Models.TUtilizador utilizador)
        {
            int usrId;


            usrId = IsValid(utilizador.username, utilizador.password);
            if (usrId>0)
            {
                Session["userID"] = usrId;
                return RedirectToAction("Index", "Empresa");
            }
            else
            {
                ModelState.AddModelError("", "Login Data is incorrect.");

            }

            return View(utilizador);
        }

        public ActionResult Logout()
        {
            Session["userID"] = null;

            return RedirectToAction("Login", "Utilizador");
        }

        [HttpGet]
        public ActionResult Create()
        {
            if(Session["userID"] == null)
                return RedirectToAction("Login", "Utilizador");
            return View();
        }

        [HttpPost]
        public ActionResult Create(Models.TUtilizador utilizador)
        {

            using (var db = new LicencingDBEntities())
            {
                var crypto = new SimpleCrypto.PBKDF2();
                var encryPass = crypto.Compute(utilizador.password);
                var sysUser = db.TUtilizador.Create();

                sysUser.username = utilizador.username;
                sysUser.nome = utilizador.nome;
                sysUser.email = utilizador.email;
                sysUser.password = encryPass;
                sysUser.passwordSalt = crypto.Salt;

                db.TUtilizador.Add(sysUser);
                db.SaveChanges();

                return RedirectToAction("Index", "Home");

            }
        }

        private int IsValid(string username, string password)
        {
            var crypto = new SimpleCrypto.PBKDF2();

            int isValid = 0;

            using (var db = new LicencingDBEntities())
            {
                var utilizador = db.TUtilizador.FirstOrDefault(u => u.username == username);
                if (utilizador != null)
                {
                    if (utilizador.password == crypto.Compute(password, utilizador.passwordSalt))
                    {
                        isValid = utilizador.idUtilizador;
                    }
                }
                return isValid;
            }
        }
    }
}