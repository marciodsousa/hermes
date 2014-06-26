using System.Web.Routing;
using RestfulRouting;
using HermesClientWebService.Controllers;
using System.Web.Mvc;

[assembly: WebActivator.PreApplicationStartMethod(typeof(HermesClientWebService.Routes), "Start")]

namespace HermesClientWebService
{
    public class Routes : RouteSet
    {
        public override void Map(IMapper map)
        {
            map.DebugRoute("routedebug");

            map.Route(new Route("",
                new RouteValueDictionary(new { controller = "Licencas", action = "Index" }),
                new MvcRouteHandler()));

            map.Resources<AuthController>(auth =>
            {
                auth.Only("index", "create");
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