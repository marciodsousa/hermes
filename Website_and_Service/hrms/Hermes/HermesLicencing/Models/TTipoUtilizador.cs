//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace HermesLicencing.Models
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    
    public partial class TTipoUtilizador
    {
        public int idTipoUtilizador { get; set; }
        public string nome { get; set; }
        public string descricao { get; set; }

        public static List<TTipoUtilizador> All()
        {
            var db = new Models.LicencingDBEntities();

            var query = db.TTipoUtilizador.Select(c => c);
            return query.ToList();
        }

        public static TTipoUtilizador GetById(int id)
        {

            var db = new Models.LicencingDBEntities();

            var query = db.TTipoUtilizador.Where(c => c.idTipoUtilizador == id).Select(c => c);
            return query.ToList().First();
        }

        public static int Update(TTipoUtilizador tuser)
        {
            int ret = 0;
            var db = new Models.LicencingDBEntities();

            var query = db.TTipoUtilizador.Select(c => c);
            var tipoUsers = query.ToList();

            if (!ValidateData(tuser))
                return 2;

            foreach (TTipoUtilizador tu in tipoUsers)
            {
                if (tuser.idTipoUtilizador == tu.idTipoUtilizador)
                {
                    tu.idTipoUtilizador = tuser.idTipoUtilizador;
                    tu.nome = tuser.nome;
                    tu.descricao = tuser.descricao;

                    db.SaveChanges();
                    ret = 1;
                    break;
                }
            }
            return ret;
        }

        public static int AddUserType(TTipoUtilizador tusr)
        {
            var db = new Models.LicencingDBEntities();

            if (!ValidateData(tusr))
                return 2;

            db.TTipoUtilizador.Add(tusr);
            db.SaveChanges();

            return tusr.idTipoUtilizador;
        }

        public static bool ValidateData(TTipoUtilizador tlic)
        {
            bool ret = true;

            if (tlic.nome == null)
                return false;
            if (tlic.descricao == null)
                return false;

            return ret;
        }
    }
}
