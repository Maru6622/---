import jetbrains.letsPlot.export.ggsave
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.geom.geomPoint
import jetbrains.letsPlot.letsPlot
import kotlin.math.PI
import kotlin.math.pow
import kotlin.random.Random

fun V (n: Int, R: Double, N:Int): Double { //функция вычисления объёма n-мерного шара, R-радиус, N-кол-во точек
    var g=0.0
    val b=(2*R).pow(n)//объём n-мерного куба, в который вписан n-мерный шар
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
        for(i in 0 until n){    //генерируем точку в n-мерном кубе, в который вписан искомый шар
            z=gen.nextDouble(-R,R)
            res[i]=z
        }
        for (c in 0 until n){   //ищем k-квадрат расстояния до начала координат
            k=k+(res[c].pow(2))
        }
        if (k<=R.pow(2)){   //проверяем её на принадлежность шару
            g=g+1.0
        }
    }
    return (b*g)/N  //искомый объём
}
fun sigma(n:Int, k:Double, N:Int): Double {     //цикл для поиска среднеквадратичного отклонения
    var s=0.0
    for (i in 0 until 1000){
        s += (V(n, 1.0, N) - PI * k).pow(2)
    }
    s=(s/1000).pow(0.5)
    return s
}
fun plot (xs:ArrayList<Int>, ys:ArrayList<Double>, number:String){  //построение графиков
    var data = mapOf<String, Any>("x" to xs, "y" to ys)
    var fig = letsPlot(data) +
            geomPoint( color = "dark-blue"
                , size = 1.0
            ){x = "x"; y = "y"}
    ggsave(fig, "$number.png")
}
fun deviation (n:Int, k:Double) {    // построение среднеквадратичного отклонения
   val S: ArrayList<Double> = ArrayList()
   val N: ArrayList<Int> = ArrayList()
   for(i in 10 .. 10000 step 10){
       S.add(sigma(n, k,i))
       N.add(i)
   }
   plot(N, S,"Sigma D=$n")
}
fun integration (D:Int, N:Int) {    //график V(D)
   val V: ArrayList<Double> = ArrayList(D)
   val n: ArrayList<Int> = ArrayList(D)
   for(i in 0 until D){
       V.add(V(i+1,1.0, N))
       n.add(i+1)
   }
   plot(n,V,"V(D)")
}

fun main(){
    val D=10
    val N=3000
    integration(D,N)
    deviation(3,4.0/3.0)
    deviation(2,1.0)
}
