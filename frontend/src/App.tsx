import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import DashboardLayout from "./components/layout/DashboardLayout";
import AgendarColeta from "./pages/AgendarColeta";
import DeclaracaoMateriais from "./pages/DeclaracaoMateriais";
import Inventario from "./pages/Inventario";
import Historico from "./pages/Historico";
import Relatorios from "./pages/Relatorios";
import Moedas from "./pages/Moedas";
import NotFound from "./pages/NotFound";
import InventoryList from "./pages/InventoryList";
import AddInventoryItem from "./pages/AddInventoryItem";
import { InventoryProvider } from "@/contexts/InventoryContext";
import ColetasAgendadasColetor from "./pages/ColetasAgendadasColetor";

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <TooltipProvider>
        <Toaster />
        <Sonner position="top-left" />
        <BrowserRouter>
          <InventoryProvider>
            <Routes>
              <Route path="/" element={<Navigate to="/agendar-coleta" replace />} />
              <Route element={<DashboardLayout />}>
                <Route path="/agendar-coleta" element={<AgendarColeta />} />
                <Route
                  path="/declaracao-materiais"
                  element={<DeclaracaoMateriais />}
                />
                <Route path="/coletas-agendadas" element={<ColetasAgendadasColetor />} />
                <Route path="/inventario" element={<InventoryList />} />
                <Route path="/inventory" element={<InventoryList />} />
                <Route path="/inventory/add" element={<AddInventoryItem />} />
                <Route path="/historico" element={<Historico />} />
                <Route path="/relatorios" element={<Relatorios />} />
                <Route path="/moedas" element={<Moedas />} />
              </Route>
              {/* ADD ALL CUSTOM ROUTES ABOVE THE CATCH-ALL "*" ROUTE */}
              <Route path="*" element={<NotFound />} />
            </Routes>
          </InventoryProvider>
        </BrowserRouter>
      </TooltipProvider>
    </QueryClientProvider>
  );
}

export default App;
