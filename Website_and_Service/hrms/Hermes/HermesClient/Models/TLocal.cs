//------------------------------------------------------------------------------
// <auto-generated>
//    This code was generated from a template.
//
//    Manual changes to this file may cause unexpected behavior in your application.
//    Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace HermesClient.Models
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.Linq;
    using System.Web.Mvc;

    public partial class TLocal
    {
        public int idLocal { get; set; }

        [Required]
        [StringLength(200)]
        [Display(Name = "Nome do local: ")]
        public string nome { get; set; }

        [Required]
        [StringLength(200)]
        [Display(Name = "Morada: ")]
        public string morada { get; set; }

        public static List<TLocal> All()
        {
            var db = new Models.PESTICliEntities();

            var query = db.TLocal.Select(c => c);
            return query.ToList();
        }

        public static TLocal GetById(int id)
        {

            var db = new Models.PESTICliEntities();

            var query = db.TLocal.Where(c => c.idLocal == id).Select(c => c);
            return query.ToList().First();
        }

        public static int Update(TLocal loc)
        {
            int ret = 0;
            var db = new Models.PESTICliEntities();

            var query = db.TLocal.Select(c => c);
            var locs = query.ToList();

            if (!ValidateData(loc))
                return 2;

            foreach (TLocal l in locs)
            {
                if (loc.idLocal == l.idLocal)
                {
                    l.idLocal = loc.idLocal;
                    l.nome = loc.nome;
                    l.morada = loc.morada;

                    db.SaveChanges();
                    ret = 1;
                    break;
                }
            }
            return ret;
        }

        public static int AddUser(TLocal loc)
        {
            var db = new Models.PESTICliEntities();

            if (!ValidateData(loc))
                return 2;

            db.TLocal.Add(loc);
            db.SaveChanges();

            return loc.idLocal;
        }

        public static bool ValidateData(TLocal loc)
        {
            bool ret = true;


            if (loc.morada == null || loc.morada.CompareTo("") == 0)
                return false;
            if (loc.nome == null && loc.nome.CompareTo("") == 0)
                return false;

            return ret;
        }
    }
}
