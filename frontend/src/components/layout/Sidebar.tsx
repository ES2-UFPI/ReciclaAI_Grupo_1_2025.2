import { Calendar, Package, Clock, BarChart3, Coins } from "lucide-react";
import { NavLink } from "react-router-dom";
import { cn } from "@/lib/utils";

const menuItems = [
  { icon: Calendar, label: "Agendar Coleta", path: "/agendar-coleta" },
  { icon: Package, label: "Inventário", path: "/inventario" },
  { icon: Clock, label: "Histórico", path: "/historico" },
  { icon: BarChart3, label: "Relatórios", path: "/relatorios" },
  { icon: Coins, label: "Moedas Verdes", path: "/moedas" },
];

const Sidebar = () => {
  return (
    <aside className="fixed left-0 top-16 h-[calc(100vh-4rem)] w-64 border-r border-border bg-sidebar">
      <nav className="flex flex-col gap-1 p-4">
        {menuItems.map((item) => (
          <NavLink
            key={item.path}
            to={item.path}
            className={({ isActive }) =>
              cn(
                "flex items-center gap-3 rounded-lg px-4 py-3 text-sm font-medium transition-all",
                "hover:bg-sidebar-accent",
                isActive
                  ? "bg-sidebar-accent text-sidebar-accent-foreground"
                  : "text-sidebar-foreground/80"
              )
            }
          >
            <item.icon className="h-5 w-5" />
            <span>{item.label}</span>
          </NavLink>
        ))}
      </nav>
    </aside>
  );
};

export default Sidebar;
