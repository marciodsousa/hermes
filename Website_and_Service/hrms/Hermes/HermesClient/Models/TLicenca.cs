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

    public partial class TLicenca
    {
        public int idLicenca { get; set; }
        public string imei { get; set; }
        public int idEmpresa { get; set; }
        public string estado { get; set; }
        public string codLicenca { get; set; }

        public virtual TEmpresa TEmpresa { get; set; }

        public static List<TLicenca> All()
        {
            var db = new Models.PESTICliEntities();

            var query = db.TLicenca.Select(c => c);
            return query.ToList();
        }

        public static TLicenca GetById(int id)
        {

            var db = new Models.PESTICliEntities();

            var query = db.TLicenca.Where(c => c.idLicenca == id).Select(c => c);
            return query.ToList().First();
        }

        public static TLicenca GetByIMEI(string imei)
        {

            var db = new Models.PESTICliEntities();

            var query = db.TLicenca.Where( x => x.imei.Equals(imei));

            var list = query.ToList();
            if (list.Count>0)
                return query.ToList().First();

            return null;
        }

        public static int Update(TLicenca lic)
        {
            int ret = 0;
            var db = new Models.PESTICliEntities();

            var query = db.TLicenca.Select(c => c);
            var licencas = query.ToList();

            if (!ValidateData(lic))
                return 2;

            foreach (TLicenca l in licencas)
            {
                if (lic.idLicenca == l.idLicenca)
                {
                    l.idLicenca = lic.idLicenca;
                    l.idEmpresa = lic.idEmpresa;
                    l.imei = lic.imei;
                    l.estado = lic.estado;
                    l.codLicenca = lic.codLicenca;

                    db.SaveChanges();
                    ret = 1;
                    break;
                }
            }
            return ret;
        }

        public static int AddLicenca(TLicenca lic)
        {
            var db = new Models.PESTICliEntities();

            if (!ValidateData(lic))
                return 2;

            db.TLicenca.Add(lic);
            db.SaveChanges();

            return lic.idLicenca;
        }

        public static bool ValidateData(TLicenca lic)
        {
            bool ret = true;

            if (lic.idLicenca == 0)
                return false;
            if (lic.idEmpresa == 0)
                return false;
            if (lic.estado == null)
                return false;
            if (lic.imei == null)
                return false;

            return ret;
        }
    }
}