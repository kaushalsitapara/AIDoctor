import pandas as pd
import csv
import re
from random import randrange

data_merg = None
data_symp = None
data_dis = None
df_symp = None
df_merg = None
df_dis = None

column_names = None
#word_list = ["highfever", "vommiting", "headache"]  # list of all possible words
pattern = None

class qgen:
    def __init__(self):
        global data_merg        
        data_merg = pd.read_csv('./MergedDS.csv', encoding ="ISO-8859-1")
        
        global data_symp
        data_symp = pd.read_csv('./Symptoms.csv', encoding ="ISO-8859-1")
        
        global data_dis
        data_dis = pd.read_csv('./Disease.csv', encoding ="ISO-8859-1")

        global df_symp
        df_symp = pd.DataFrame(data_symp)
        
        global df_merg
        df_merg = pd.DataFrame(data_merg)
        
        global df_dis
        df_dis = pd.DataFrame(data_dis)

        global column_names 
        column_names = list(df_symp.name)
            #word_list = ["highfever", "vommiting", "headache"]  # list of all possible words
        global pattern        
        pattern = re.compile("|".join("%s" % word for word in list(column_names)))

    def pred_d(self,lisym,lisds):
        print(lisym)
        print(lisds)

    def mainSort(self,sinp):
        s = sinp
        result = re.findall(pattern, s) #here finding symptoms from sentence spoken by user and column names
        ic = 0
        sympmatch = []

        #symptoms fetching from dataset and its key
        for ic in range(len(result)):
          for index, row in df_symp.iterrows():
            if result[ic] == row['name']:
              sympmatch.append(row['key'])
              print(row['name']," Key : ",row['key'])
          ic+=1
          


        #here which disease contains that symptoms 1 like dengue fever:0 unmatched, malaria fever:1 matched
        dismatch = []
        ic = 0
        for ic in range(len(sympmatch)):
          for index, row in df_merg.iterrows():
            if sympmatch[ic] == row['Symptom']:
              if row['Disease'] not in dismatch:
                dismatch.append(row['Disease'])
              #print(row['Symptom']," Key : ",row['Disease'])
          ic+=1
          


        #now recommand new symtomps from both and all of fetched disease
        df_merg.iloc[0]['Symptom']
        '''
        ic=0
        for ic  in range(len(dismatch)):  
          for index, row in df_merg.iterrows():
            if dismatch[ic] == row['Disease']:
              dismatch.append(row['Disease'])
              print(row['Symptom']," Key : ",row['Disease'])
          ic+=1
          '''

        #select randomsymptom from symptom.csv where disease='disease'
       
        dislen = len(dismatch)
        qcount = 0
        qprev = ''
        currnum = ''
        sym = ''
        ssz = []
        sym1 = []
        ddz = []

        #add condition like don't recommand one symtopm 2 time and don't recomand symptom with already spoken by user and in symptom matched list
        if dislen>1:
          while qcount !=5:
            currnum = randrange(0,dislen)
            if qprev != currnum :
              
              lsd = df_merg[df_merg['Disease'] == dismatch[currnum]]#dismatch['D1']
              sym1 = lsd.sample()
              sym = sym1.Symptom.values[0]
              
              if (sym not in ssz) == True:
                ssz.append(sym)
                ddz.append(sym1.Disease.values[0])
                #print(sym," currnum : ",currnum," prevnum :",qprev)
                qprev = currnum
                qcount+=1
              
              elif (sym in ssz) == True:
                sym1 = lsd.sample()
            
            
            
            
            elif qprev == currnum:
              currnum = randrange(0,dislen)
              #print("here ",currnum)
            
        dc = {'Symp':ssz,'Dis':ddz}
        return dc            
            #qs = qgen()             
            #qs.pred_d(ssz,ddz)
