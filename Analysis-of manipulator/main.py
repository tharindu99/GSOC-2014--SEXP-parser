import pprint
import scipy
import numpy
import scipy.linalg
import timeit
import json

#A = scipy.array([ [4,-1,-1,0], [-1,4,0,-1], [-1,0,4,-1],[0,-1,-1,4] ]) 
#QR_s = json.dumps(range(10)) 

file_qr = open("data_array_qr.js","a")
file_lu = open("data_array_lu.js","a")

def QR_dec(A):
	start = timeit.default_timer()
	Q, R = scipy.linalg.qr(A)

	#print ("QR decomposition Done!")
	stop = timeit.default_timer()
	
	aa = (stop - start)*1000
	return "%.2f" % aa

def LU_dec(A):
	start = timeit.default_timer()
	P, L, U = scipy.linalg.lu(A)

	stop = timeit.default_timer()
	bb = (stop - start)*1000
	return "%.2f" % bb





count = 0
for i in range(1,30):
	A = numpy.random.random((i*50,i*50))
	a = QR_dec(A)
	print ("QR done!.",a,"ms")
	b = "data1["+str(i-1)+"]="+str(a)+";\n"
	file_qr.write(b)

	
	c = LU_dec(A)
	print ("LU done!.",c,"ms")
	d = "data2["+str(i-1)+"]="+str(c)+";\n"
	file_lu.write(d)


file.close	
