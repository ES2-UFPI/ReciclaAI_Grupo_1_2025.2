import { useAuth } from '@/contexts/AuthContext';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Recycle, Calendar, Package, TrendingUp, Coins } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { useNavigate } from 'react-router-dom';

const Index = () => {
  const { user } = useAuth();
  const navigate = useNavigate();

  const getWelcomeMessage = () => {
    if (user?.tipoPessoa === 'PRODUTOR') {
      return 'Gerencie seus materiais recicláveis e agende coletas';
    } else if (user?.tipoPessoa === 'COLETOR') {
      return 'Organize suas rotas e gerencie coletas';
    } else if (user?.tipoPessoa === 'RECEPTOR') {
      return 'Acompanhe o recebimento de materiais recicláveis';
    }
    return 'Sistema de gestão de reciclagem inteligente';
  };

  const getQuickActions = () => {
    if (user?.tipoPessoa === 'PRODUTOR') {
      return [
        {
          title: 'Agendar Coleta',
          description: 'Solicite a coleta dos seus materiais',
          icon: Calendar,
          action: () => navigate('/agendar-coleta'),
          color: 'bg-blue-500',
        },
        {
          title: 'Inventário',
          description: 'Visualize seus materiais disponíveis',
          icon: Package,
          action: () => navigate('/inventario'),
          color: 'bg-green-500',
        },
        {
          title: 'Moedas Verdes',
          description: 'Consulte suas recompensas',
          icon: Coins,
          action: () => navigate('/moedas'),
          color: 'bg-yellow-500',
        },
      ];
    } else if (user?.tipoPessoa === 'COLETOR') {
      return [
        {
          title: 'Criar Coleta',
          description: 'Registre uma nova rota de coleta',
          icon: Recycle,
          action: () => navigate('/criar-coleta'),
          color: 'bg-green-500',
        },
        {
          title: 'Coletas Agendadas',
          description: 'Veja suas coletas programadas',
          icon: Calendar,
          action: () => navigate('/coletas-agendadas'),
          color: 'bg-blue-500',
        },
        {
          title: 'Inventário',
          description: 'Materiais coletados',
          icon: Package,
          action: () => navigate('/inventario'),
          color: 'bg-purple-500',
        },
      ];
    } else {
      return [
        {
          title: 'Inventário',
          description: 'Materiais recebidos',
          icon: Package,
          action: () => navigate('/inventario'),
          color: 'bg-green-500',
        },
        {
          title: 'Histórico',
          description: 'Acompanhe o histórico',
          icon: TrendingUp,
          action: () => navigate('/historico'),
          color: 'bg-blue-500',
        },
        {
          title: 'Relatórios',
          description: 'Visualize estatísticas',
          icon: TrendingUp,
          action: () => navigate('/relatorios'),
          color: 'bg-purple-500',
        },
      ];
    }
  };

  const quickActions = getQuickActions();

  return (
    <div className="min-h-screen bg-background p-6">
      <div className="max-w-7xl mx-auto space-y-8">
        {/* Header Section */}
        <div className="text-center space-y-4">
          <div className="flex justify-center">
            <div className="bg-green-600 p-4 rounded-full">
              <Recycle className="w-12 h-12 text-white" />
            </div>
          </div>
          <h1 className="text-4xl font-bold text-foreground">
            Bem-vindo ao ReciclaAI, {user?.nome || 'Usuário'}!
          </h1>
          <p className="text-xl text-muted-foreground max-w-2xl mx-auto">
            {getWelcomeMessage()}
          </p>
        </div>

        {/* Quick Actions */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {quickActions.map((action, index) => {
            const Icon = action.icon;
            return (
              <Card 
                key={index} 
                className="hover:shadow-lg transition-shadow cursor-pointer"
                onClick={action.action}
              >
                <CardHeader>
                  <div className={`${action.color} w-12 h-12 rounded-lg flex items-center justify-center mb-4`}>
                    <Icon className="w-6 h-6 text-white" />
                  </div>
                  <CardTitle>{action.title}</CardTitle>
                  <CardDescription>{action.description}</CardDescription>
                </CardHeader>
                <CardContent>
                  <Button 
                    className="w-full !bg-green-600 !text-white hover:!bg-green-700"
                    onClick={(e) => {
                      e.stopPropagation();
                      action.action();
                    }}
                  >
                    Acessar
                  </Button>
                </CardContent>
              </Card>
            );
          })}
        </div>

        {/* Info Section */}
        <Card className="bg-gradient-to-r from-green-50 to-green-100 border-green-200">
          <CardHeader>
            <CardTitle className="text-green-800">Sobre o ReciclaAI</CardTitle>
          </CardHeader>
          <CardContent className="text-green-700">
            <p className="leading-relaxed">
              O ReciclaAI é uma plataforma inteligente para gestão de reciclagem que conecta 
              produtores, coletores e receptores de materiais recicláveis. Nossa missão é 
              facilitar o processo de reciclagem, aumentar a eficiência da coleta e contribuir 
              para um futuro mais sustentável.
            </p>
          </CardContent>
        </Card>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Impacto Ambiental
              </CardTitle>
              <Recycle className="h-4 w-4 text-green-600" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-green-600">+100%</div>
              <p className="text-xs text-muted-foreground">
                Contribuindo para um planeta melhor
              </p>
            </CardContent>
          </Card>
          
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Eficiência
              </CardTitle>
              <TrendingUp className="h-4 w-4 text-blue-600" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-blue-600">Otimizada</div>
              <p className="text-xs text-muted-foreground">
                Gestão inteligente de recursos
              </p>
            </CardContent>
          </Card>
          
          <Card>
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">
                Sustentabilidade
              </CardTitle>
              <Package className="h-4 w-4 text-purple-600" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold text-purple-600">Garantida</div>
              <p className="text-xs text-muted-foreground">
                Economia circular em prática
              </p>
            </CardContent>
          </Card>
        </div>
      </div>
    </div>
  );
};

export default Index;
