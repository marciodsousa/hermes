using System.Web.Routing;
using RestfulRouting;
using HermesLicencing.Controllers;
using System.Web.Mvc;

[assembly: WebActivator.PreApplicationStartMethod(typeof(HermesLicencing.Routes), "Start")]

namespace HermesLicencing
{
    public class Routes : RouteSet
    {
        public override void Map(IMapper map)
        {
            map.DebugRoute("routedebug");

            map.Route(new Route("",
                new RouteValueDictionary(new { controller = "Empresas", action = "Index" }),
                new MvcRouteHandler()));

            map.Resources<EmpresasController>(empresas =>
            {
                empresas.Only("index", "show", "create", "update");
            });
            map.Resources<LicencasController>(licencas =>
            {
                licencas.Only("index", "show", "create", "update");
            });

        }

        public static void Start()
        {
            var routes = RouteTable.Routes;
            routes.MapRoutes<Routes>();
        }
    }
}