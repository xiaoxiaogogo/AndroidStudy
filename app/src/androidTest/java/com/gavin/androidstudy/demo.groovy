
/*
    声明变量: def
     1.声明变量的类型可以省略
     2.方法的返回值可以省略
     3.方法调用的时候 括号可以省略

 */

def a = 1
def b = 'haha'
def String c = "a=${a}, b=${b}" // 这是在字符串拼接外部的变量
def sayHi(String c){
    println c
    return 12
}
sayHi(a + b + c)

/*
    字符串：
        单引号： 同java中的String类型
        双引号： 可以使用${}
        三引号： 字符串可以自由换行
 */


println '''我爱你，
   baby，
你是否也爱我'''


/*
    闭包：可以作为参数，也可以作为返回值，也可以直接调用。这个一个特殊的数据类型

 */

def closure1 = {String str ->
    println(str)
}

// 这里没有类型的参数不能是上面已经定义的变量
def closure2 = { p1, p2 ->
    // 注意： 闭包可以访问外部的变量； 但是 普通的方法是无法访问外部的变量的
    a + b // 默认， return
}
closure1 'fuck'
println closure2(5, 6)

// 默认参数 it
def closure3 = {
    println('默认参数it的值：${it}.....')
}
closure3('default val')


/*
    增强型List : List和Map都可以用[]表示， 和python类似，使用也很相似  ； 可以自动累加
        重点： << 左移操作符  ： 向List尾部添加新数据

    range：继承自List 1...100
 */

def emptyList = []
def list = ['hello', 100, 'word']
list[2] = 200
list << 'fuck'
println list[2]
println list.size()


def emptyMap = [:]
def map = ['key1': 100, 'key2':'hello']


