import { Toaster } from "@/components/ui/toaster";
import { Toaster as Sonner } from "@/components/ui/sonner";
import { TooltipProvider } from "@/components/ui/tooltip";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import DashboardLayout from "./components/layout/DashboardLayout";
import AgendarColeta from "./pages/AgendarColeta";
import AgendarBeneficiamento from "./pages/AgendarBeneficiamento";
import DeclaracaoMateriais from "./pages/DeclaracaoMateriais";
import DeclaracaoMateriaisBeneficiamento from "./pages/DeclaracaoMateriaisBeneficiamento";
import Inventario from "./pages/Inventario";
import Historico from "./pages/Historico";
import Relatorios from "./pages/Relatorios";
import NotFound from "./pages/NotFound";
import InventoryList from "./pages/InventoryList";
import AddInventoryItem from "./pages/AddInventoryItem";
import { InventoryProvider } from "@/contexts/InventoryContext";
import ColetasAgendadasColetor from "./pages/ColetasAgendadasColetor";
import BeneficiamentosAgendadosReceptor from "./pages/BeneficiamentosAgendadosReceptor";
import CriarColeta from "./pages/CriarColeta";
import CriarBeneficiamento from "./pages/CriarBeneficiamento";
import InformarMateriaisColeta from "./pages/InformarMateriaisColeta";
import InformarMateriaisBeneficiamento from "./pages/InformarMateriaisBeneficiamento";
import FormularioColeta from "./pages/FormularioColeta";
import FormularioBeneficiamento from "./pages/FormularioBeneficiamento";
import { AuthProvider } from "@/contexts/AuthContext";
import { ProtectedRoute } from "@/components/ProtectedRoute";
import Login from "./pages/Login";
import Index from "./pages/Index";

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <TooltipProvider>
        <AuthProvider>
          <Toaster />
          <Sonner position="top-left" />
          <BrowserRouter>
            <Routes>
              <Route path="/login" element={<Login />} />
              <Route
                path="/"
                element={
                  <ProtectedRoute>
                    <DashboardLayout />
                  </ProtectedRoute>
                }
              >
                <Route index element={<Index />} />
                <Route path="agendar-coleta" element={<AgendarColeta />} />
                <Route path="agendar-beneficiamento" element={<AgendarBeneficiamento />} />
                <Route path="inventario" element={<InventoryList />} />
                <Route path="coletas-agendadas" element={<ColetasAgendadasColetor />} />
                <Route path="historico" element={<Historico />} />
                <Route path="relatorios" element={<Relatorios />} />
                <Route
                  path="declaracao-materiais"
                  element={<DeclaracaoMateriais />}
                />
                <Route
                  path="declaracao-materiais-beneficiamento"
                  element={<DeclaracaoMateriaisBeneficiamento />}
                />
                <Route
                  path="coletas-agendadas-coletor"
                  element={<ColetasAgendadasColetor />}
                />
                <Route
                  path="beneficiamentos-agendados-receptor"
                  element={<BeneficiamentosAgendadosReceptor />}
                />
                <Route path="criar-coleta" element={<CriarColeta />} />
                <Route path="criar-beneficiamento" element={<CriarBeneficiamento />} />
                <Route path="formulario-coleta" element={<FormularioColeta />} />
                <Route path="formulario-beneficiamento" element={<FormularioBeneficiamento />} />
                <Route
                  path="informar-materiais-coleta"
                  element={<InformarMateriaisColeta />}
                />
                <Route
                  path="informar-materiais-beneficiamento"
                  element={<InformarMateriaisBeneficiamento />}
                />
                <Route path="*" element={<NotFound />} />
              </Route>
            </Routes>
          </BrowserRouter>
        </AuthProvider>
      </TooltipProvider>
    </QueryClientProvider>
  );
}

export default App;
