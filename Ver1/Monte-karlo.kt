import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.letsPlot
import kotlin.math.PI
import kotlin.math.pow
import kotlin.random.Random

fun V (n: Int, R: Double, N:Int): Double { //функция вычисления объёма n-мерного шара, R-радиус, N-кол-во точек
var g=0.0
val b=(2*R).pow(n)//объём n-мерного куба, в который вписан n-мерный шар
val V:Double
var k:Double
var z:Double
val gen= Random(100000)
val res: ArrayList<Double> = ArrayList(n)
for(i in 0 until n)
{
res.add(gen.nextDouble(1.0))
}
for(j in 1 until N){
k=0.0
for(i in 0 until n){ //генерируем точку в n-мерном кубе, в который вписан искомый шар
z=gen.nextDouble(-R,R)
res[i]=z
}
for (c in 0 until n){//ищем k-квадрат расстояния до начала координат
k=k+(res[c].pow(2))
}
if (k<=R.pow(2)){//проверяем её на принадлежность шару
g=g+1.0
}
}
V= (b*g)/N//искомый объём
return V
}
fun sigma(o:Double, k:Double): Double {//цикл для поиска среднеквадратичного откланения
var s=0.0
for (i in 0 until 1000){
s=s+(o-PI*k).pow(2)
}
s=(s/1000).pow(0.5)
return s
}

fun main(){
val D=10
    
val Y: ArrayList<Double> = ArrayList(D)//построение графика 1/х^0.5 для последующего сравнения с графиком сигмы
val X: ArrayList<Int> = ArrayList(D)
for(i in 100 .. 10000){
X.add(10*i)
Y.add((10*i).toDouble().pow(-0.5))
}

val data4 = mapOf<String, Any>("Y" to Y, "X" to X)
val fig4 = letsPlot(data4) + geomPoint(
color = "dark-green",
size = 1.0
) { x = "X"; y = "Y" }
ggsave(fig4, filename = "x^(-0.5).png")

val V: ArrayList<Double> = ArrayList(D)//построение графика V(D)
val n: ArrayList<Int> = ArrayList(D)

for(i in 0 until D){
V.add(V(i+1,1.0, 100000))
n.add(i+1)
}

val data = mapOf<String, Any>("V" to V, "D" to n)
val fig = letsPlot(data) + geomPoint(
color = "dark-blue",
size = 1.0
) { x = "D"; y = "V" }
ggsave(fig, "V(D).png")

val S1: ArrayList<Double> = ArrayList(D)//построение сигмы при D=2
val N1: ArrayList<Int> = ArrayList(D)

for(i in 100 .. 30000){
S1.add(sigma(V(2, 1.0, 10*i), 1.0))
N1.add(10*i)
}

val data1 = mapOf<String, Any>("S" to S1, "N" to N1)
val fig1 = letsPlot(data1) + geomPoint(
color = "dark-blue",
size = 1.0
) { x = "N"; y = "S" }
ggsave(fig1, "Sigma D=2.png")

val S2: ArrayList<Double> = ArrayList(D)//построение графика сигмы при D=3
val N2: ArrayList<Int> = ArrayList(D)

for(i in 100 .. 10000){
S2.add(sigma(V(3, 1.0, 10*i), 4.0/3.0))
N2.add(10*i)
}

val data2 = mapOf<String, Any>("S2" to S2, "N2" to N2)
val fig2 = letsPlot(data2) + geomPoint(
color = "dark-blue",
size = 1.0
) { x = "N2"; y = "S2" }
ggsave(fig2, "Sigma D=3.png")

}
