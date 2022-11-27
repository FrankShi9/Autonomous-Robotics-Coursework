# Pose Class Definition
# File: pose.py
# Date: 7th Jan 2022
# Description: Pose Class support for COMP329 Programming Assignment (2022)
# Author: Terry Payne (trp@liv.ac.uk)

#import numpy as np
import math

class Pose:
    """ 
    A class to represent the pose of some object (robot, sensor, etc)
    
    ...
    
    Attributes
    ----------
    x : float
        x in local coordinate system
    y : float
        y in local coordinate system
    ˚theta : float
        heading in radians from the axis y=0, in the range -pi ... +pi 

    Methods
    -------
    set_position(x, y, theta):
        updates all the parameters of the pose instance

    """
    def __init__(self, x=0.0, y=0.0, new_theta=0.0):
#    def __init__(self, x, y, new_theta):
        """
        Constructs all the necessary attributes for the pose object.

        Parameters
        ----------
        x : float
            x in local coordinate system
        y : float
            y in local coordinate system
        theta : float
            heading in radians from the axis y=0, in the range -pi ... +pi 
        """
        self.set_position(x, y, new_theta)
        #self.x = x
        #self.y = y
        #self._theta = new_theta

        
    @property
    def theta(self):
        return self._theta
        
    @theta.setter
    def theta(self, new_theta):
        if new_theta > math.pi:
            self._theta = new_theta - (2 * math.pi)
        elif new_theta < -math.pi:
            self._theta = (2 * math.pi) + new_theta
        else:
            self._theta = new_theta

    def set_position(self, x, y, new_theta):
        """
        updates all the parameters of the pose instance

        If the value of theta is outside a the valit range, it is converted
        into a value between -pi ... +pi
        
        Parameters
        ----------
        x : float
            x in local coordinate system
        y : float
            y in local coordinate system
        theta : float
            heading in radians from the axis y=0, in the range -pi ... +pi 
            
        Returns
        -------
        None
        """
        self.x = x
        self.y = y
        self.theta = new_theta

    def get_dtheta(self, t):
        """ Find the difference in radians between some heading and the current pose """
        d = t - self.theta
        if (d > math.pi):
            d = -(2 * math.pi) + d
        elif (d < -math.pi):
            d = (2 * math.pi) + d
        return d
        

    def __str__(self):
        return '({0:.2f}, {1:.2f}, {2:.2f})'.format(self.x, self.y, self._theta)
