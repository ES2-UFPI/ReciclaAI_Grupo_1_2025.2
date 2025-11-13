import { NavLink } from "react-router-dom";
import { Calendar, Package, Clock, Coins, PlusCircle, Factory, Truck, CheckCircle } from "lucide-react";
import { useAuth } from "@/contexts/AuthContext";

interface MenuItem {
  icon: any;
  label: string;
  path: string;
  allowedTypes: ("PRODUTOR" | "COLETOR" | "RECEPTOR")[];
}

const allMenuItems: MenuItem[] = [
  {
    icon: Calendar,
    label: "Agendar Coleta",
    path: "/agendar-coleta",
    allowedTypes: ["PRODUTOR"],
  },
  {
    icon: PlusCircle,
    label: "Criar Coleta",
    path: "/criar-coleta",
    allowedTypes: ["COLETOR"],
  },
  {
    icon: Calendar,
    label: "Coletas Agendadas",
    path: "/coletas-agendadas-coletor",
    allowedTypes: ["COLETOR"],
  },
  {
    icon: Truck,
    label: "Agendar Beneficiamento",
    path: "/agendar-beneficiamento",
    allowedTypes: ["COLETOR"],
  },
  {
    icon: Factory,
    label: "Criar Beneficiamento",
    path: "/criar-beneficiamento",
    allowedTypes: ["RECEPTOR"],
  },
  {
    icon: CheckCircle,
    label: "Beneficiamentos Agendados",
    path: "/beneficiamentos-agendados-receptor",
    allowedTypes: ["RECEPTOR"],
  },
  {
    icon: Package,
    label: "Inventário",
    path: "/inventario",
    allowedTypes: ["PRODUTOR", "COLETOR", "RECEPTOR"],
  },
  {
    icon: Clock,
    label: "Histórico",
    path: "/historico",
    allowedTypes: ["PRODUTOR", "COLETOR", "RECEPTOR"],
  },
  {
    icon: Coins,
    label: "Moedas Verdes",
    path: "/moedas",
    allowedTypes: ["PRODUTOR"],
  },
];

const Sidebar = () => {
  const { user } = useAuth();

  // Filter menu items based on user type
  const menuItems = allMenuItems.filter((item) =>
    user?.tipoPessoa && item.allowedTypes.includes(user.tipoPessoa)
  );

  return (
    <aside className="fixed left-0 top-16 h-[calc(100vh-4rem)] w-64 border-r border-border bg-card">
      <nav className="flex flex-col gap-2 p-4">
        {menuItems.map((item) => {
          const Icon = item.icon;
          return (
            <NavLink
              key={item.path}
              to={item.path}
              className={({ isActive }) =>
                `flex items-center gap-3 rounded-lg px-3 py-2 text-sm font-medium transition-colors
                ${
                  isActive
                    ? "bg-primary text-primary-foreground"
                    : "text-muted-foreground hover:bg-accent hover:text-accent-foreground"
                }`
              }
            >
              <Icon className="h-5 w-5" />
              {item.label}
            </NavLink>
          );
        })}
      </nav>
    </aside>
  );
};

export default Sidebar;
