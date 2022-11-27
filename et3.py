import math
import numpy as np

class Robot():
	def __init__(self, pose=(0,0,0), z):
		self.pose = pose
		self.m0 = (3,2)
		self.m1 = (4,3)
		self.m2 = (2.5,5)

		self.ah = .8
		self.rand = .2
		self.hit = .5

		self.zmax = 10
		self.xz = np.zeros((1,6))
		self.yz = np.zeros((1,6))


	def query(self, pose=(0,0,0), z=np.zeros((1,6)), xk=np.zeros((1,6)), yk=np.zeros((1,6)), th_k=np.zeros(1,6), m):
		q = 1
		x = pose[0]
		y = pose[1]
		theta = pose[2]

		xy_occupy = []

		for ele in m:
			xy_occupy.append((l2((x,y), ele)))

		for i in range(6):
			if z[0, i] != zmax:
				xz[0, i] = x + xk[0, i]*np.cos(theta)-yk[0, i]*np.sin(theta) + z[0, i]*np.cos(theta+th_k[0, i])
				yz[0, i] = y + yk[0, i]*np.cos(theta)+xk[0, i]*np.sin(theta) + z[0, i]*np.sin(theta+th_k[0, i])

				dist = math.sqrt(np.min(xy_occupy))
				p = .8 * self.normal(dist, .5) + (.2/self.zmax) 
				q *= p

		return q 


	def normal(self, a, b):
		return 1/(math.sqrt(2*math.pi*(b**2))) * math.e**((-1/2)*(a**2)/(b**2))

	def l2((x,y), (mx,my)):
		return (x-mx)**2 + (y-my)**2

