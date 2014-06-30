using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using HermesClient.Models;

namespace HermesClientWebService.Controllers
{
    public class UtilizadoresController : ApplicationController
    {
        //
        // GET: /Utilizadores/

        PESTICliEntities db = new PESTICliEntities();

        public ActionResult Index()
        {
            return Json(TUtilizador.All(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int id)
        {
            var user = TUtilizador.GetById(id);
            if (user == null)
            {
                return new HttpNotFoundResult();
            }
            else
            {
                return Json(user, JsonRequestBehavior.AllowGet);
                
            }
        }

        public ActionResult Update(TUtilizador user)
        {
            ActionResult result = null;

            switch (TUtilizador.Update(user))
            {
                case 1: //utilizador actualizado com sucesso
                    result = Json(TUtilizador.GetById(user.idUtilizador));
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

        public static int IsValid(string _username, string _password)
        {
            var crypto = new SimpleCrypto.PBKDF2();

            int isValid = 0;

            PESTICliEntities db = new PESTICliEntities();

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